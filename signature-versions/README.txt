See more in https://blog.csdn.net/liushuimpc/article/details/109329754

Below data are based on signature-versions/v1 at https://gitlab.com/androidapkutils/apks/-/tree/master/signature-versions/v1.

####################       Topic 1       ########################
MANIFEST.MF: Calculate the hash digest of all the files.

-----------------------------------------------------------------
Example: AndroidManifest.xml in decompressed package.

==== File name: MANIFEST.MF ====
Name: AndroidManifest.xml
SHA-256-Digest: asXILMe0fyJ3N0udzUAB8VtAUHXfKiFaoWWFy/cMYfU=

Name: META-INF/androidx.activity_activity.version
SHA-256-Digest: WYVJhIUxBN9cNT4vaBoV/HkkdC+aLkaMKa8kjc5FzgM=

Name: META-INF/androidx.appcompat_appcompat-resources.version
SHA-256-Digest: FXXhr0qV8S9wtO5qatzoFglT2T6hfcJhG5CIPMw607g=

-----------------------------------------------------------------

Step 1.
$ sha256sum AndroidManifest.xml 
6ac5c82cc7b47f2277374b9dcd4001f15b405075df2a215aa16585cbf70c61f5  AndroidManifest.xml

Step 2.
$ echo -n '6ac5c82cc7b47f2277374b9dcd4001f15b405075df2a215aa16585cbf70c61f5' > /tmp/a.txt
$ cat /tmp/a.txt | xxd -r -p | base64
asXILMe0fyJ3N0udzUAB8VtAUHXfKiFaoWWFy/cMYfU=

Online transfer website: http://tomeko.net/online_tools/hex_to_base64.php?lang=en




####################       Topic 2       ########################
CERT.SF: Calculate the hash digest of each part message in MANIFEST.MF.

-----------------------------------------------------------------
Example: AndroidManifest.xml part in MANIFEST.MF.

Note: below 3 lines are ASCII text, with CRLF(\r\n) line terminators.

==== File name: CERT.SF ====
Line1	Name: AndroidManifest.xml
Line2	SHA-256-Digest: JuhNmIbD2j51JYBib3XjukcEqsAQ/99zSchelsUbzIc=
Line3	

-----------------------------------------------------------------

Step 1.
Copy this 3 lines into a new file /tmp/1.txt
This step requires to keep '\r\n' at the end of each lines.
Since I cannot find a good way in Ubuntu to check/add it, I use Notepad++ in Windows to confirm it.

Note:	Notepad++ View -> Show Symbol -> Show All Characters.

Step 2.
$ sha256sum /tmp/1.txt
26e84d9886c3da3e752580626f75e3ba4704aac010ffdf7349c85e96c51bcc87 /tmp/1.txt

Step 3.
$ echo -n '26e84d9886c3da3e752580626f75e3ba4704aac010ffdf7349c85e96c51bcc87' > /tmp/2.txt
$ cat /tmp/2.txt | xxd -r -p | base64
JuhNmIbD2j51JYBib3XjukcEqsAQ/99zSchelsUbzIc=




####################       Topic 3       ########################
CERT.SF: Calculate the hash digest of whole MANIFEST.MF.

-----------------------------------------------------------------
Example in CERT.SF:

==== File name: MANIFEST.MF ====
SHA-256-Digest-Manifest: yx7SikZYrD8YHUtNjVswz8pylXwwWqObyIQrb652oeo=

-----------------------------------------------------------------

Step 1.
$ sha256sum MANIFEST.MF
cb1ed28a4658ac3f181d4b4d8d5b30cfca72957c305aa39bc8842b6fae76a1ea  MANIFEST.MF

Step 2.
$ echo -n 'cb1ed28a4658ac3f181d4b4d8d5b30cfca72957c305aa39bc8842b6fae76a1ea' > /tmp/x.txt
$ cat /tmp/x.txt | xxd -r -p | base64
yx7SikZYrD8YHUtNjVswz8pylXwwWqObyIQrb652oeo=




####################       Topic 4       ########################
CERT.RSA
- show information from CERT.RSA

$ openssl asn1parse -inform DER -in CERT.RSA -dump

$ openssl pkcs7 -inform DER -in CERT.RSA -text -noout -print_certs

$ openssl pkcs7 -in CERT.RSA -inform DER -out cert.pem