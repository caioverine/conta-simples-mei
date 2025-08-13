import api from "./api";

export const realizarLogin = async (email: string, senha: string) => {
  return api.post("/auth/login", { email, senha });
}