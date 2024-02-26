type JwtPayload = {
  sub: string;
  userUrl: string;
  iat: number;
  exp: number;
};

export default JwtPayload;
