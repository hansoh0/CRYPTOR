
# CRYPTOR

A file & directory encryptor written in Java created by @hansoh0 (https://www.github.com/hansoh0)

## Installation
```
git clone https://github.com/hansoh0/CRYPTOR.git
```
## How to Use
```
java FileByteMan --help
Usage: {encrypt/decrypt} {directory} {password/key} {salt}
```
### Example
```
:~$ cat /target/directory/file.txt
Hello there

:~$ java FileByteMan encrypt /target/directory SecretKey1 salt
File Encrypted: file.txt

:~$ cat /target/directory/file.txt
p�L��f�%����|�%Nt���n��26�8�v֑

:~$ java FileByteMan decrypt /target/directory SecretKey1 salt
File Encrypted: file.txt

:~$ cat /target/directory/file.txt
Hello there
```
This took me too long as is, bug fixes and polishes inbound

I AM NOT RESPONSIBLE FOR MISUSE
