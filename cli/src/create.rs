use clap::{Args, Parser, Subcommand};

#[derive(Args)]
pub(crate) struct CreateBucket {
    name: String,
    path: String,
    template: String,
}

const REPO_URL: &str = "https://github.com/LinwoodCloud/Bucket/archive/main.zip";

pub(crate) fn create_bucket(create : CreateBucket) {
    // Download repo and extract template subdirectory to path
    println!("Downloading repo...");
    let mut resp = reqwest::get(REPO_URL).expect("Failed to download repo");
    let mut zip = std::io::BufReader::new(resp.body().as_slice());
    let mut archive = zip::ZipArchive::new(&mut zip).expect("Failed to read zip");
    let mut example_path = create.path.clone();
    std::fs::create_dir_all(&example_path).expect("Failed to create example directory");
    // Only extract the template subdirectory
    for i in 0..archive.len() {
        // Test if the file starts with the template directory
        if archive.by_index(i).unwrap().name.starts_with(format!("/examples/{}", &create.template)) {
            let mut file = archive.by_index(i).unwrap();
            let mut out = std::fs::File::create(example_path.clone() + &file.name).expect("Failed to create file");
            file.unpack(&mut out).expect("Failed to unpack file");
        }
    }
}
