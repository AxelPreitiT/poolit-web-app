"use client";

import Image from "next/image";

const StaticImage = ({ src, alt, ...props }) => (
  <Image
    src={`${process.env.NEXT_PUBLIC_BASE_PATH}/${src}`}
    alt={alt}
    {...props}
  />
);

export default StaticImage;
