import type { Despesa } from "../model/despesa-model";
import type { DespesaPage } from "../model/despesa-page-model";
import type { DespesaFormData } from "../pages/Despesas/ModalNovaDespesa";
import api from "./api";

export const listarDespesas = async (page: number, size: number) => {
  return api.get<DespesaPage>("/despesas", {
    params: { page, size }
  });
}

export const salvarDespesa = async (dados: DespesaFormData) => {
  return api.post<Despesa>("/despesas", dados);
};

export const atualizarDespesa = async (dados: DespesaFormData) => {
  return api.put<Despesa>(`/despesas/${dados.id}`, dados);
};

export const excluirDespesa = async (id: string) => {
  return api.delete(`/despesas/${id}`);
}