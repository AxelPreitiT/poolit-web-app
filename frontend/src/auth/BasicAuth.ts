import InvalidBasicAuthUserIdError from "@/errors/InvalidBasicAuthUserIdError";

type BasicAuthHeaderValueType = `Basic ${string}`;

class BasicAuth {
  public static encode: (
    username: string,
    password: string
  ) => BasicAuthHeaderValueType = (username: string, password: string) => {
    if (username.includes(":")) {
      // RFC 7617, Section 2.1
      throw new InvalidBasicAuthUserIdError(
        "user-id cannot contain colon (:) characters"
      );
    }
    const encodedCredentials: string = btoa(`${username}:${password}`);
    return `Basic ${encodedCredentials}`;
  };
}

export default BasicAuth;
