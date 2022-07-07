use std::io::Write;

use api::meta::{BucketMeta, BucketProperties};
use clap::Args;

#[derive(Debug, Args)]
pub(crate) struct CreateCommand {
    #[clap(value_parser)]
    name: String,
}

pub(crate) fn run_create(cmd : CreateCommand) {
    let meta = BucketMeta::new(cmd.name.clone(), "".to_string(), vec![]);
    let meta_yaml = serde_yaml::to_string(&meta).unwrap();
    let meta_path = format!("{}/meta.yaml", cmd.name);
    // Create cmd.name directory
    std::fs::create_dir_all(&cmd.name).unwrap();
    let mut meta_file = std::fs::File::create(&meta_path).unwrap();
    // Create file with meta data
    meta_file.write_all(meta_yaml.as_bytes()).unwrap();
    println!("Created file in {}", &cmd.name);
}