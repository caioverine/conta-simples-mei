import api from "./api";
import type { ReceitaFormData } from "../pages/Receitas/ModalNovaReceita";
import type { ReceitaPage } from "../model/receita-page-model";
import type { Receita } from "../model/receita-model";

export const listarReceitas = async (page: number, size: number) => {
  return api.get<ReceitaPage>("/receitas", {
    params: { page, size }
  });
}

export const salvarReceita = async (dados: ReceitaFormData) => {
  return api.post<Receita>("/receitas", dados);
};

export const atualizarReceita = async (dados: ReceitaFormData) => {
  return api.put<Receita>(`/receitas/${dados.id}`, dados);
};

export const excluirReceita = async (id: string) => {
  return api.delete(`/receitas/${id}`);
}
