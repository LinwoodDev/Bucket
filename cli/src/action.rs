use std::error::Error;

use api::action::{BucketRequest, BucketAction};
use clap::{Subcommand, Args};
use gray_matter::engine::YAML;
use gray_matter::{Matter};



#[derive(Debug, Subcommand)]
pub(crate) enum ActionCommand {
    List(ActionList),
    Markdown(MarkdownCommand),
    Yaml(YamlAction),
    Run(RunAction),
}
#[derive(Debug, Args)]
#[clap(args_conflicts_with_subcommands = true)]
pub(crate) struct RunAction {
    #[clap(subcommand)]
    command: Option<BucketAction>,
}

#[derive(Debug, Args)]
pub(crate) struct ActionList {

}

#[derive(Debug, Args)]
pub(crate) struct YamlAction {
    data : String,
    user : String
}

#[derive(Debug, Args)]
pub(crate) struct MarkdownCommand {
    data : String,
    user : String
}

pub(crate) fn run_markdown(cmd : MarkdownCommand) -> Result<BucketRequest, Box<dyn Error>> {
    let matter = Matter::<YAML>::new();
    let data = matter.parse(&cmd.data).data;
    // Test if data is Some
    match data {
        Some(data) => {
            let action = data.deserialize()?;
            let request = BucketRequest::new(action, cmd.user);
            Ok(request)
        },
        None => {
            Err(Box::new(std::io::Error::new(std::io::ErrorKind::Other, "Could not parse data")))
        }
    }
}
