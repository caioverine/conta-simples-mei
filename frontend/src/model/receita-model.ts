import type { Categoria } from "./categoria-model";

export interface Receita {
  id: string;
  categoria: Categoria;
  descricao: string;
  valor: number;
  data: string;
}