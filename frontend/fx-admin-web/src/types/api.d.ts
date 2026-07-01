declare namespace API {
  interface Response<T = any> { code: number; message: string; data: T; }
  interface PageResult<T = any> { list: T[]; total: number; }
}
