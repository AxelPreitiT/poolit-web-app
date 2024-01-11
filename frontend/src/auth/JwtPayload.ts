type JwtPayload = {
  email: string;
  role: string;
  iat: number;
  exp: number;
};

export default JwtPayload;
