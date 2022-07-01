use std::error::Error;
use gray_matter::engine::YAML;
use gray_matter::{Matter, Pod};

pub(crate) fn parse_body(body : &str) -> Option<Pod> {
    let matter = Matter::<YAML>::new();
    matter.parse(body).data
}

enum BucketAction {
    AddAsset {
        asset : String,
        description : String,
    },
    PostUpdate {
        asset : String,
        description : String,
        name: String,
        track: String,
    },
    RemoveAsset {
        asset : String,
    },
    RemoveUser {
        user : String,
    },
}