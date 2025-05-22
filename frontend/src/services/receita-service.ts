import api from "./api";
import type { ReceitaFormData } from "../components/ModalNovaReceita";

export const salvarReceita = async (dados: ReceitaFormData) => {
  return api.post("/receitas", dados);
};
