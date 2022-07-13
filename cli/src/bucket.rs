use std::io::Error;
use api::asset::Asset;
use crate::yaml::load_yaml;
use api::meta::BucketMeta;

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

    pub fn load_meta(&self) -> Result<BucketMeta, Box<dyn std::error::Error>> {
        let path = String::from(&self.path) + "/meta";
        let yaml = load_yaml(&path)?;
        let meta = serde_yaml::from_str(&yaml)?;
        Ok(meta)
    }

    pub fn load_assets(&self) -> Result<Vec<Asset>, Box<dyn std::error::Error>> {
        let mut assets = Vec::new();
        for entry in std::fs::read_dir(&self.path)? {
            let entry = entry?;
            let path = entry.path();
            if path.is_dir() {
                let name = path.strip_prefix(&self.path)?.to_str();
                if let Some(name) = name {
                    let asset = self.load_asset(name)?;
                    assets.push(asset);
                }
            }
        }
        Ok(assets)
    }

    pub fn load_asset(&self, name: &str) -> Result<Asset, Box<dyn std::error::Error>> {
        let path = String::from(&self.path) + "/assets/" + name;
        let yaml = load_yaml(&path)?;
        let meta = serde_yaml::from_str(&yaml)?;
        Ok(meta)
    }
}