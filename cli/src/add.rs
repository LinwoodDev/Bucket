use clap::{Args, Parser, Subcommand};

#[derive(Args)]
struct AddAsset {
    #[clap(value_parser)]
    name: Option<String>,
    #[clap(value_parser)]
    description: Option<String>,
}

enum BucketOperation {
    AddAsset,
    AddUser,

}

pub fn add_asset() {
}
