
# CRYPTOR

A file & directory encryptor written in Java. This cryptor uses AES256 and encrypts files by bytes, allowing for encryption of many different types of files. created by @hansoh0 (https://www.github.com/hansoh0)

## Installation
```
hansoho@hansoho.git:~$ git clone https://github.com/hansoh0/CRYPTOR.git
```
## Compiling & Install
```
# This comes precompiled but if you wanna do it yourself, go for it:
hansoho@hansoho.git:~$ javac -d bin/ src/*java
# Without the following alias definition you would have to run FileByteMan with 'java ~/src/FileByteMan {options}'
hansoho@hansoho.git:~$ echo "alias FileByteMan='java $HOME/bin/FileByteMan'" >> ~/.bashrc
hansoho@hansoho.git:~$ source ~/.bashrc
```
## How to Use
```
hansoho@hansoho.git:~$ FileByteMan --help
Usage: FileByteMan {encrypt/decrypt} {directory} {password/key} {salt}
```
### Example
```
hansoho@hansoho.git:~$ cat /target/directory/file.txt
Hello there

hansoho@hansoho.git:~$ FileByteMan encrypt /target/directory SecretKey1 salt
File Encrypted: file.txt

hansoho@hansoho.git:~$ cat /target/directory/file.txt
p�L��f�%����|�%Nt���n��26�8�v֑

hansoho@hansoho.git:~$ FileByteMan decrypt /target/directory SecretKey1 salt
File Decrypted: file.txt

hansoho@hansoho.git:~$ cat /target/directory/file.txt
Hello there
```
This took me too long as is, lots of improvements to be had... bug fixes and polishes inbound.

I AM NOT RESPONSIBLE FOR MISUSE
