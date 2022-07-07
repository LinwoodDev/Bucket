use serde::{Serialize, Deserialize};

#[derive(Serialize, Deserialize, Debug)]
pub struct BucketUser {
    name: String,
}

impl BucketUser {
    pub fn new(name: String) -> BucketUser {
        BucketUser {
            name,
        }
    }
    pub fn name(&self) -> &str {
        &self.name
    }
}
