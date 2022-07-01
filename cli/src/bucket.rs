use std::fmt::Error;
use crate::yaml::load_yaml;

struct Bucket {
    path : String,
}

impl Bucket {
    pub fn new(path: String) -> Bucket {
        Bucket {
            path,
        }
    }
    pub fn path(&self) -> &str {
        &self.path
    }

    pub fn load_meta(&self) -> Result<BucketMeta, Error> {
        let yaml = load_yaml(&self.path + "/meta")?;
        serde_yaml::from_str(&yaml)?
    }
}