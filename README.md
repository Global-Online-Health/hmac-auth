# hmac-auth

A Java library for creating HMAC Signature for request authentication

# How to build the library

Run tests

`./mvnw test`

Create jar file

`./mvnw package`

Install jar file in your local .m2 repository

`./mvnw install`

Publish a new version on the repository

`./mvnw deploy`

# How to generate a valid test signature

A helper test `generateSignatureFromGivenInputs` has been created at (`~/core/HmacAuthenticationSignerTest.java`) 
that will run the algorithm to generate the required components needed to build the string to sign and then sign it 
with a given secret access key. 

Before running the tests the request inputs needed are:
* `method` - this is the request method, for instance `GET` or `POST` etc;
* `path` - this is the request path excluding the query params if there are any;
* `queryString` - this represents the string value of the query parameters, for instance if a request would have a 
 multi-value parameters (ie `embed=["plan", "coverage"])`) the string value of it would be `embed=[\"plan\", \"coverage\"]`;
* `accessKey` - this is the access key id of the secret access key, to be used for generating the hmac request;
* `secretAccessKey` - this is the base64 value of the secret access used to sign the string to sign.

The output on the console will include the timestamp used to generate the signature 
and the generated hmac signature in base64 format:
```
Signature Timestamp: 1664456981
Generated Hmac Signature: rMB+J0VH8PCWY09MDljzXO1q8j5T8l+FORMQ56RVfPs=
```

## License

Copyright © 2022

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.



