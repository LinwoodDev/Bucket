use clap::{Subcommand, Args};
use gray_matter::engine::YAML;
use gray_matter::{Matter, Pod};



#[derive(Debug, Subcommand)]
pub(crate) enum ActionCommand {
    List(ActionList),
    Markdown(MarkdownAction),
    Yaml(YamlAction),
    Run(RunAction),
}
#[derive(Debug, Args)]
#[clap(args_conflicts_with_subcommands = true)]
struct RunAction {
    #[clap(subcommand)]
    command: Option<BucketAction>,
}

#[derive(Debug, Args)]
struct ActionList {

}

#[derive(Debug, Args)]
struct YamlAction {
    data : String
}

#[derive(Debug, Args)]
struct MarkdownAction {
    data : String
}

pub(crate) fn parse_body(body : &str) -> Option<Pod> {
    let matter = Matter::<YAML>::new();
    matter.parse(body).data
}

#[derive(Debug, Subcommand)]
enum BucketAction {
    #[clap(arg_required_else_help = true)]
    AddAsset {
        #[clap(value_parser)]
        asset : String,
        #[clap(value_parser)]
        description : String,
    },
    #[clap(arg_required_else_help = true)]
    PostUpdate {
        #[clap(value_parser)]
        asset : String,
        #[clap(value_parser)]
        description : String,
        #[clap(value_parser)]
        name: String,
        #[clap(value_parser)]
        track: String,
    },
    #[clap(arg_required_else_help = true)]
    RemoveAsset {
        #[clap(value_parser)]
        asset : String,
    },
    #[clap(arg_required_else_help = true)]
    RemoveUser {
        #[clap(value_parser)]
        user : String,
    },
}