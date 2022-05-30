use std::rc::Rc;
use crate::user::BucketUser;

pub struct Asset {
    name: String,
    description: String,
    source: String,
    website: String,
    license: String,
    tracks: Vec<AssetTrack>,
    maintainers: Vec<Rc<BucketUser>>,
}

pub struct AssetTrack {
    name: String,
    description: String,
    source: String,
    updates: Vec<AssetUpdate>,
    owner: Rc<Asset>,
}

pub struct AssetUpdate {
    name: String,
    description: String,
    downloads: Vec<AssetDownload>,
    owner: Rc<AssetTrack>,
}

pub struct AssetDownload {
    name: String,
    link: String,
    owner: Rc<AssetUpdate>,
}
