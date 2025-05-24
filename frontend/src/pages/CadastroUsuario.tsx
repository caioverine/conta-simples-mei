import logoGraficoSemFundo from '../assets/logo_grafico_sem_fundo.png';
import axios from "axios";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { useNavigate } from "react-router-dom";

export default function CadastroUsuario() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaConfirmada, setSenhaConfirmada] = useState("");
  const navigate = useNavigate();

  const handleCadastro = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        email: email,
        senha: senha,
      });

      navigate("/login");
    } catch (err) {
      console.log(err);
      alert("Erro ao cadastrar");
    }
  };

  return (
    <div className="container flex items-center justify-center min-h-screen px-6 mx-auto">
      <div className="w-full max-w-xl p-8">
        <div className="flex-1">
          <div className="text-center">
            <div className="flex justify-center mb-4">
              <img className="w-auto h-20 sm:h-20" src={logoGraficoSemFundo} alt=""/>
            </div>

            <p className="mt-3 text-gray-500 dark:text-gray-300">Cadastre-se</p>
          </div>

          <div className="mt-8">
            <form onSubmit={handleCadastro}>
              <div>
                {/*<label className="block mb-2 text-sm text-gray-600 dark:text-gray-200">Email</label>*/}
                <input type="email" name="email" id="email" placeholder="usuario@mail.com" value={email} onChange={(e) => setEmail(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
              </div>

              <div className="mt-6">
                {/*<div className="flex justify-between mb-2">
                  <label className="text-sm text-gray-600 dark:text-gray-200">Senha</label>
                </div>*/}

                <input type="password" name="password" id="password" placeholder="Sua senha" value={senha} onChange={(e) => setSenha(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
              </div>

              <div className="mt-6">
                {/*<div className="flex justify-between mb-2">
                  <label className="text-sm text-gray-600 dark:text-gray-200">Confirme senha</label>
                </div>*/}

                <input type="password" name="passwordConf" id="passwordConf" placeholder="Confirme sua senha" value={senhaConfirmada} onChange={(e) => setSenhaConfirmada(e.target.value)}
                  className="block w-full px-4 py-2 mt-2 text-gray-700 placeholder-gray-400 bg-white border border-gray-200 rounded-lg dark:placeholder-gray-600 dark:bg-gray-900 dark:text-gray-300 dark:border-gray-700 focus:border-blue-400 dark:focus:border-blue-400 focus:ring-blue-400 focus:outline-none focus:ring focus:ring-opacity-40" />
              </div>

              <div className="mt-6">
                <button type="submit" 
                  className="w-full px-4 py-2 tracking-wide text-white transition-colors duration-300 transform rounded-lg focus:outline-none focus:ring focus:ring-blue-300 focus:ring-opacity-50">
                  Cadastrar
                </button>
              </div>
            </form>

            <p className="mt-6 text-sm text-center text-gray-400">Já tem conta?
            <Link to="/login" className="text-blue-500 focus:outline-none focus:underline hover:underline"> Entre</Link>.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}