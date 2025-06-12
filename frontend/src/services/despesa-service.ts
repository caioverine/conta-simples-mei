import type { DespesaPage } from "../model/despesa-page-model";
import api from "./api";

export const listarDespesas = async (page: number, size: number) => {
  return api.get<DespesaPage>("/despesas", {
    params: { page, size }
  });
}