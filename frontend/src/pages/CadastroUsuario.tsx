import axios from "axios";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

export default function CadastroUsuario() {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaConfirmada, setSenhaConfirmada] = useState("");
  const navigate = useNavigate();

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/usuarios", {
        nome: nome,
        email: email,
        senha: senha,
        perfil: "USER"
      });

      console.log(response.data);
      alert("Usuário cadastrado com sucesso!");

      navigate("/login");
    } catch (err) {
      console.log(err);
      alert("Erro ao cadastrar");
    }
  };

  return (
    <div className="flex flex-col justify-center items-center min-h-screen px-2 sm:px-4 bg-white dark:bg-gray-900">
      <div className="w-full max-w-md sm:max-w-lg md:max-w-xl p-6 sm:p-8 bg-white dark:bg-gray-800 rounded-lg shadow-md">
        <div className="flex-1">
          <div className="mt-4 sm:mt-8">
            <form onSubmit={handleCadastro}>
              <div>
                <input
                  type="nome"
                  name="nome"
                  id="nome"
                  placeholder="Nome"
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                />
              </div>

              <div className="mt-6">
                <input
                  type="email"
                  name="email"
                  id="email"
                  placeholder="Email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40"
                />
              </div>

              <div className="mt-6">
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
                  className="w-full px-4 py-2 tracking-wide text-white bg-blue-600 hover:bg-blue-700 transition-colors duration-300 transform rounded-lg focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-50"
                >
                  Cadastrar
                </button>
              </div>
            </form>

            <p className="mt-6 text-sm text-center text-gray-400">
              Já tem conta?
              <Link to="/login" className="text-blue-500 focus:outline-none focus:underline hover:underline"> Entre</Link>.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}