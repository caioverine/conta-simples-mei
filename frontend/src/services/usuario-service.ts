import type { Usuario } from "../model/usuario-model";
import api from "./api";

export const cadastrarUsuario = async (dados: Usuario) => {
  return api.post("/usuarios", dados);
}