use clap::Subcommand;
use serde::Deserialize;

#[derive(Debug, Subcommand, Deserialize)]
pub enum BucketAction {
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

pub struct BucketRequest {
    action: BucketAction,
    user: String,
}

impl BucketRequest {
    pub fn new(action: BucketAction, user: String) -> BucketRequest {
        BucketRequest {
            action,
            user,
        }
    }

    pub fn action(&self) -> &BucketAction {
        &self.action
    }

    pub fn user(&self) -> &String {
        &self.user
    }
}
