const equalRefine =
  <A extends object>(fieldOne: keyof A, fieldTwo: keyof A) =>
  (data: A) =>
    data[fieldOne] === data[fieldTwo];

export default equalRefine;
