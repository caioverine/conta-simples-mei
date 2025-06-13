import type { Categoria } from "../model/categoria-model";
import api from "./api";

export const listarCategorias = async () => {
  return api.get<Categoria[]>("/categorias");
}

export const listarCategoriasReceita = async () => {
  return api.get<Categoria[]>(`/categorias/tipo/RECEITA`);
}