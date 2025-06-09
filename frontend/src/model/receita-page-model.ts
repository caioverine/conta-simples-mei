import type { Receita } from "./receita-model";

export interface ReceitaPage {
  content: Receita[];
  totalPages: number;
  totalElements: number;
  number: number;
  size: number;
  last: boolean;
  first: boolean;
  empty: boolean;
}