struct Asset<'a> {
    name: String,
    description: String,
    source: String,
    website: String,
    license: String,
    tracks: Vec<AssetTrack<'a>>
}

struct AssetTrack<'a> {
    name: String,
    description: String,
    source: String,
    updates: Vec<AssetUpdate>,
    asset: &'a Asset<'a>
}

struct AssetUpdate {
    name: String,
    description: String,
    downloads: Vec<AssetDownload>
}

struct AssetDownload {
    name: String,
    link: String,
}
