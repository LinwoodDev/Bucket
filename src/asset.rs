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

impl Asset {
    pub(crate) fn new(name: String, maintainer: Rc<BucketUser>) -> Asset {
        Asset {
            name,
            description: String::new(),
            source: String::new(),
            website: String::new(),
            license: String::new(),
            tracks: Vec::new(),
            maintainers: vec![maintainer],
        }
    }

    pub fn name(&self) -> &str {
        &self.name
    }

    pub fn description(&self) -> &str {
        &self.description
    }

    pub fn source(&self) -> &str {
        &self.source
    }

    pub fn website(&self) -> &str {
        &self.website
    }

    pub fn license(&self) -> &str {
        &self.license
    }

    pub fn tracks(&self) -> &Vec<AssetTrack> {
        &self.tracks
    }

    pub fn maintainers(&self) -> &Vec<Rc<BucketUser>> {
        &self.maintainers
    }

    pub fn add_track(&mut self, track: AssetTrack) {
        if self.tracks.iter().any(|t| t.name == track.name) {
            return;
        }
        self.tracks.push(track);
    }

    pub fn add_maintainer(&mut self, maintainer: Rc<BucketUser>) {
        if self.maintainers.iter().any(|m| m.name() == maintainer.name()) {
            return;
        }
        self.maintainers.push(maintainer);
    }

    pub fn remove_maintainer(&mut self, maintainer: Rc<BucketUser>) {
        self.maintainers.retain(|u| u.name() != maintainer.name());
    }

    pub fn remove_track(&mut self, track: AssetTrack) {
        self.tracks.retain(|t| t.name() != track.name());
    }

    pub fn remove_track_by_name(&mut self, name: &str) {
        self.tracks.retain(|t| t.name() != name);
    }
}

pub struct AssetTrack {
    name: String,
    description: String,
    source: String,
    updates: Vec<AssetUpdate>,
}

impl AssetTrack {
    pub(crate) fn new(name: String) -> AssetTrack {
        AssetTrack {
            name,
            description: String::new(),
            source: String::new(),
            updates: Vec::new(),
        }
    }

    pub fn name(&self) -> &str {
        &self.name
    }

    pub fn description(&self) -> &str {
        &self.description
    }

    pub fn source(&self) -> &str {
        &self.source
    }

    pub fn updates(&self) -> &Vec<AssetUpdate> {
        &self.updates
    }

    pub fn owner(&self) -> &Asset {
        &self.owner
    }

    pub fn add_update(&mut self, update: AssetUpdate) {
        self.updates.push(update);
    }
}

pub struct AssetUpdate {
    name: String,
    description: String,
    downloads: Vec<AssetDownload>,
}

impl AssetUpdate {
    pub(crate) fn new(name: String) -> AssetUpdate {
        AssetUpdate {
            name,
            description: String::new(),
            downloads: Vec::new(),
        }
    }

    pub fn name(&self) -> &str {
        &self.name
    }

    pub fn description(&self) -> &str {
        &self.description
    }

    pub fn downloads(&self) -> &Vec<AssetDownload> {
        &self.downloads
    }

    pub fn owner(&self) -> &AssetTrack {
        &self.owner
    }

    pub fn add_download(&mut self, download: AssetDownload) {
        self.downloads.push(download);
    }

    pub fn get_download_names(&self) -> Vec<String> {
        self.downloads.iter().map(|d| d.name.clone()).collect()
    }

    pub fn get_download_by_name(&self, name: &str) -> Option<&AssetDownload> {
        self.downloads.iter().find(|d| d.name == name)
    }
}

pub struct AssetDownload {
    name: String,
    link: String,
}
