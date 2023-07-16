# JSON Database

A client-server application that allows the clients to store their data on the server in JSON format.

This project is a part of the track [Java Developer](https://hyperskill.org/tracks/17?category=2) provided by [JetBrains Academy](https://www.jetbrains.com/academy/)

## Getting Started

### Prerequisites

Java 8+

## Usage

The arguments will be passed to the client in the following format:

`-t set -k "Some key" -v "Here is some text to store on the server"`

`-t` is the type of the request, and `-k` is the key. `-v` is the value to save in the database: you only need it in case of a set request.

Alternatively there ir a possibility to read a request from a file. If the `-in` argument is followed by the file name provided, read a request from that file. The file will be stored in /client/data.

Here are some examples of the input file contents:

`{"type":"set","key":"name","value":"Kate"}`

`{"type":"get","key":"name"}`

`{"type":"delete","key":"name"}`

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

Distributed under the [MIT License](https://choosealicense.com/licenses/mit/)