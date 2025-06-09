import api from "./api";
import type { ReceitaFormData } from "../components/ModalNovaReceita";
import type { ReceitaPage } from "../model/receita-page-model";

export const listarReceitas = async (page: number, size: number) => {
  return api.get<ReceitaPage>("/receitas", {
    params: { page, size }
  });
}

export const salvarReceita = async (dados: ReceitaFormData) => {
  return api.post("/receitas", dados);
};
