use std::io::Error;
use api::asset::BucketAsset;
use crate::yaml::load_yaml;
use api::BucketAsset;
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

    pub fn load_meta(&self) -> Result<BucketMeta, Error> {
        let yaml = load_yaml(String::from(&self.path) + "/meta")?;
        serde_yaml::from_str(&yaml)?
    }

    pub fn load_assets(&self) -> Result<Vec<BucketAsset>, Error> {
        let mut assets = Vec::new();
        for entry in std::fs::read_dir(&self.path)? {
            let entry = entry?;
            let path = entry.path();
            if path.is_dir() {
                let asset = self.load_asset(path.strip_prefix(&self.path)?.to_str()?)?;
                assets.push(asset);
            }
        }
        Ok(assets)
    }

    pub fn load_asset(&self, name: &str) -> Result<BucketAsset, Error> {
        let path = String::from(&self.path) + "/assets/" + name;
        let yaml = load_yaml(path)?;
        serde_yaml::from_str(&yaml)?
    }
}