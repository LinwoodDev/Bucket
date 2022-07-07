use std::collections::HashMap;
use serde::{Serialize, Deserialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct BucketMeta {
    name : String,
    description : String,
    whitelist: Vec<String>,
    properties: AssetProperties,
    categories: HashMap<String, BucketCategory>,
}
#[derive(Serialize, Deserialize, Debug)]
pub struct AssetProperties {
    enable_updates: bool,
    edit_updates: bool,
    enable_deletes: bool,
    enable_uploads: bool,
    enable_external_uploads: bool,
}

impl BucketMeta {
    pub fn new(name: String, description: String, whitelist: Vec<String>, properties: AssetProperties) -> BucketMeta {
        BucketMeta {
            name,
            description,
            whitelist,
            properties,
            categories: HashMap::new(),
        }
    }
}

#[derive(Serialize, Deserialize, Debug)]
pub struct BucketCategory {
    display_name: String,
    description: String,
    parent: String,
}