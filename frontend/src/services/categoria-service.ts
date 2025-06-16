import type { Categoria } from "../model/categoria-model";
import type { CategoriaFormData } from "../pages/Categorias/ModalNovaCategoria";
import api from "./api";

export const listarCategorias = async () => {
  return api.get<Categoria[]>("/categorias");
}

export const listarCategoriasReceita = async () => {
  return api.get<Categoria[]>(`/categorias/tipo/RECEITA`);
}

export const listarCategoriasDespesa = async () => {
  return api.get<Categoria[]>(`/categorias/tipo/DESPESA`);
}

export const salvarCategoria = async (dados: CategoriaFormData) => {
  return api.post<Categoria>("/categorias", dados);
}

export const excluirCatefgoria = async (id: string) => {
  return api.delete(`/categorias/${id}`);
}