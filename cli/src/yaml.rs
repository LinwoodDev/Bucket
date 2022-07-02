use std::fs::File;
use std::io::{Read, Error, ErrorKind};


fn load_yaml_file(path: &str) -> Result<String, Error> {
    let mut file = File::open(path)?;
    let mut contents = String::new();
    file.read_to_string(&mut contents)?;
    Ok(contents)
}

/// This method searches for a file ending with `.yaml`, .yml` or `.json` and returns the contents of the file as a `String`.
pub(crate) fn load_yaml(path: &str) -> Result<String, Error> {
    // Try to load .yaml
    let yaml_path = format!("{}.yaml", path);
    let yaml = load_yaml_file(&yaml_path);
    if yaml.is_ok() {
        return yaml;
    }
    // Try to load .yml
    let yaml_path = format!("{}.yml", path);
    let yaml = load_yaml_file(&yaml_path);
    if yaml.is_ok() {
        return yaml;
    }

    // Try to load .json
    let yaml_path = format!("{}.json", path);
    let yaml = load_yaml_file(&yaml_path);
    if yaml.is_ok() {
        return yaml;
    }
    Err(Error::new(ErrorKind::NotFound, "No yaml file found"))
}
