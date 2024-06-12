
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
hansoho@hansoho.git:~$ cat /target/directory/file.txt
Hello there

hansoho@hansoho.git:~$ java FileByteMan encrypt /target/directory SecretKey1 salt
File Encrypted: file.txt

hansoho@hansoho.git:~$ cat /target/directory/file.txt
p�L��f�%����|�%Nt���n��26�8�v֑

hansoho@hansoho.git:~$ java FileByteMan decrypt /target/directory SecretKey1 salt
File Decrypted: file.txt

hansoho@hansoho.git:~$ cat /target/directory/file.txt
Hello there
```
This took me too long as is, lots of improvements to be had... bug fixes and polishes inbound.

I AM NOT RESPONSIBLE FOR MISUSE
