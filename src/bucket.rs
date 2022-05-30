use std::rc::Rc;
use crate::asset::Asset;
use crate::user::BucketUser;

pub struct Bucket {
    name: String,
    description: String,
    assets: Vec<Asset>,
    users: Vec<BucketUser>,
}
impl Bucket {
    pub fn new(name: String, description: String) -> Bucket {
        Bucket {
            name,
            description,
            assets: Vec::new(),
            users: Vec::new(),
        }
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
}

