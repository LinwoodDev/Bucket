mod add;
mod bucket;
mod yaml;
mod action;
mod create;

use clap::{Args, Parser, Subcommand};
use crate::add::{add_asset, AddAsset};
use crate::create::{create_bucket, CreateBucket};

const VERSION: &str = env!("CARGO_PKG_VERSION");


#[derive(Parser)]
#[clap(author, version, about, long_about = None)]
#[clap(propagate_version = true)]
struct Cli {
    #[clap(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
    Create(CreateBucket),
    /// Adds files to myapp
    Add(AddAsset),
}

fn main() {
    let args = Cli::parse();
    match args.command {
        Commands::Add(_cmd) => {
            add_asset();
        },
        Commands::Create(cmd) => {
            create_bucket(cmd);
        }
    }
}
