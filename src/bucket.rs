use std::fmt::Error;
use std::rc::Rc;
use crate::asset::Asset;
use crate::user::BucketUser;
use serde::{Serialize, Deserialize};


#[derive(Debug, PartialEq, Serialize, Deserialize)]
pub struct Bucket {
    name: String,
    description: String,
    assets: Vec<Asset>,
    users: Vec<BucketUser>,
    #[serde(skip)]
    path: String,
}

impl Bucket {
    pub fn new(name: String, description: String, path: String) -> Result<Bucket, Error> {
        let bucket = Bucket {
            name,
            description,
            path,
            assets: Vec::new(),
            users: Vec::new(),
        };
        match bucket.save_meta() {
            Ok(_) => Ok(bucket),
            Err(e) => Err(e),
        }
    }
    pub fn load(path: String) -> Result<Bucket, Error> {
        let serialized = std::fs::read_to_string(&path)?;
        let mut bucket : Bucket = serde_yaml::from_str(&serialized)?;
        bucket.path = path;
        Ok(bucket)
    }
    pub fn add_asset(&mut self, name: String, maintainer: Rc<BucketUser>) {
        let asset = Asset::new(name, maintainer);
        self.assets.push(asset);
    }
    pub fn add_user(&mut self, user: BucketUser) {
        if self.users.iter().any(|u| u.name() == user.name()) {
            return;
        }
        self.users.push(user);
    }
    pub fn get_maintained_assets(&self, user: &BucketUser) -> Vec<&Asset> {
        self.assets
            .iter()
            .filter(|asset| asset.maintainers().iter().any(|m| m.name() == user.name()))
            .collect()
    }
    pub fn name(&self) -> &str {
        &self.name
    }
    pub fn description(&self) -> &str {
        &self.description
    }
    pub fn assets(&self) -> &Vec<Asset> {
        &self.assets
    }
    pub fn users(&self) -> &Vec<BucketUser> {
        &self.users
    }

    pub fn save_meta(&self) -> Result<(), Error> {
        let s = serde_yaml::to_string(&self)?;
        let path = self.path.clone() + "/meta.yaml";
        std::fs::write(path, s)?;
        Ok(())
    }
}

