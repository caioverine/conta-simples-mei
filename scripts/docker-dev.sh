#!/bin/bash

# Script para facilitar desenvolvimento com Docker

case "$1" in
  "start")
    echo "🚀 Iniciando ambiente de desenvolvimento..."
    docker compose up -d
    ;;
  "stop")
    echo "🛑 Parando containers..."
    docker compose down
    ;;
  "restart")
    echo "🔄 Reiniciando containers..."
    docker compose restart
    ;;
  "logs")
    echo "📋 Mostrando logs..."
    docker compose logs -f ${2:-backend}
    ;;
  "clean")
    echo "🧹 Limpando containers e volumes..."
    docker compose down -v --rmi all
    ;;
  "shell")
    echo "🐚 Acessando shell do container..."
    docker compose exec ${2:-backend} sh
    ;;
  *)
    echo "Uso: $0 {start|stop|restart|logs|clean|shell}"
    echo ""
    echo "Comandos:"
    echo "  start   - Inicia todos os containers"
    echo "  stop    - Para todos os containers"
    echo "  restart - Reinicia containers"
    echo "  logs    - Mostra logs (opcional: nome do serviço)"
    echo "  clean   - Remove containers, volumes e imagens"
    echo "  shell   - Acessa shell do container (opcional: nome do serviço)"
    exit 1
    ;;
esac
