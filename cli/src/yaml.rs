use std::io::{Error, ErrorKind};
use std::fs::File;
use std::io::Read;


fn load_yaml_file(path: String) -> Result<String, Error> {
    let mut file = File::open(path)?;
    let mut contents = String::new();
    file.read_to_string(&mut contents)?;
    Ok(contents)
}

/// This method searches for a file ending with `.yaml`, .yml` or `.json` and returns the contents of the file as a `String`.
pub(crate) fn load_yaml(path: String) -> Result<String, Error> {
    // Try to load .yaml
    let yaml = load_yaml_file(String::from(&path) + ".yaml");
    if yaml.is_ok() {
        return yaml;
    }
    // Try to load .yml
    let yaml = load_yaml_file(String::from(&path) + ".yml");
    if yaml.is_ok() {
        return yaml;
    }

    // Try to load .json
    let yaml = load_yaml_file(String::from(&path) + ".json");
    if yaml.is_ok() {
        return yaml;
    }
    Err(Error::new(ErrorKind::NotFound, "No yaml file found"))
}
