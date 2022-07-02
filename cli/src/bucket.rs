use std::error::Error;

use api::meta::BucketMeta;

use crate::yaml::load_yaml;

struct Bucket {
    path : String,
}

enum LoadError {
    MyIoError(std::io::Error)
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

    pub fn load_meta(&self) -> Result<BucketMeta, Box<dyn Error>> {
        let meta_path = String::from(&self.path) + "/meta";
        let yaml = load_yaml(&meta_path)?;
        let meta = serde_yaml::from_str(&yaml)?;
        Ok(meta)
    }

    pub fn load_asset(&self, name: &str) -> Result<String, Box<dyn Error>> {
        let asset_path = String::from(&self.path) + "/assets/" + name;
        let yaml = load_yaml(&asset_path)?;
        let asset = serde_yaml::from_str(&yaml)?;
        Ok(asset)
    }
}