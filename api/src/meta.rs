use serde::{Serialize, Deserialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct BucketMeta {
    name : String,
    description : String,
    whitelist: Vec<String>,
    properties: BucketProperties
}

#[derive(Serialize, Deserialize, Debug)]
pub struct BucketProperties {
    enable_updates: bool,
    edit_updates: bool,
    enable_deletes: bool,
    enable_uploads: bool,
}

impl BucketMeta {
    pub fn new(name: String, description: String, whitelist: Vec<String>, properties: BucketProperties) -> BucketMeta {
        BucketMeta {
            name,
            description,
            whitelist,
            properties
        }
    }
}
