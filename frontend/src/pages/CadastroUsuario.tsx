import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import type { Usuario } from "../model/usuario-model";
import { cadastrarUsuario } from "../services/usuario-service";
import { ModalSucesso } from "../components/ModalSucesso";

export default function CadastroUsuario() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaConfirmada, setSenhaConfirmada] = useState("");
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();

    if (senha !== senhaConfirmada) {
      alert("As senhas não coincidem.");
      return;
    }

    try {
      const usuario: Usuario = {
        nome,
        email,
        senha,
        perfil: "USER"
      };

      await cadastrarUsuario(usuario);

      setSuccess("Usuário cadastrado com sucesso!");
      navigate("/login");
    } catch (err) {
      console.log(err);
      alert("Erro ao cadastrar");
    }
  };

  return (
    <>
      <div className="flex flex-col justify-center items-center min-h-screen px-2 sm:px-4 bg-white dark:bg-gray-900">
        <div className="w-full max-w-md sm:max-w-lg md:max-w-xl p-6 sm:p-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
          <div className="flex-1">
            <div className="mt-4 sm:mt-8">
              <form onSubmit={handleCadastro}>
                <div>
                  <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Nome</label>
                  <input
                    type="text"
                    name="nome"
                    id="nome"
                    placeholder="Nome"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                  />
                </div>

                <div className="mt-6">
                  <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Email</label>
                  <input
                    type="email"
                    name="email"
                    id="email"
                    placeholder="usuario@mail.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                  />
                </div>

                <div className="mt-6">
                  <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Senha</label>
                  <input
                    type="password"
                    name="password"
                    id="password"
                    placeholder="Sua senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                  />
                </div>

                <div className="mt-6">
                  <label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Confirme sua senha</label>
                  <input
                    type="password"
                    name="passwordConf"
                    id="passwordConf"
                    placeholder="Confirme sua senha"
                    value={senhaConfirmada}
                    onChange={(e) => setSenhaConfirmada(e.target.value)}
                    className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                  />
                </div>

                <div className="mt-6">
                  <button
                    type="submit"
                    className="btn w-full px-4 py-2 tracking-wide"
                  >
                    Cadastrar
                  </button>
                </div>
              </form>

              <p className="mt-6 text-sm text-center text-gray-400">
                Já tem conta?
                <Link to="/login" className="focus:outline-none focus:underline hover:underline"> Entre</Link>.
              </p>
            </div>
          </div>
        </div>
      </div>
      {success && (
        <ModalSucesso
          mensagem={success}
          onClose={() => setSuccess(null)}
        />
      )}
    </>
  );
}