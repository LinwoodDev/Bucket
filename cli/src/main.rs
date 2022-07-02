mod create;
mod bucket;
mod yaml;
mod action;

use action::ActionCommand;
use clap::{Parser, Subcommand};
use crate::create::{run_create, CreateCommand};


#[derive(Parser)]
#[clap(author, version, about, long_about = None)]
#[clap(propagate_version = true)]
struct Cli {
    #[clap(subcommand)]
    command: Commands,
}

#[derive(Debug, Subcommand)]
enum Commands {
    /// Adds files to myapp
    #[clap(subcommand)]
    Action(ActionCommand),
    Create(CreateCommand)
}

fn main() {
    let args = Cli::parse();
    match args.command {
        Commands::Create(cmd) => {
            run_create(cmd);
        },
        Commands::Action(cmd) => {
            println!("{:?}", cmd);
        }
    }
}
