## DocxFormattingChecker
### A tool for checking and fixing MS Word document formatting.

Needs a `.docx` document that you'd like to check and/or fix and a `config.json` with a set of formatting rules.\

See examples of `config.json` in `src/test/resources`

### Run app
    ./gradlew run --args="/Path/To/Config.json /Path/To/Document.docx"

### Run tests
    ./gradlew test