package com.contasimplesmei.repository

import com.contasimplesmei.model.Receita
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ReceitaRepository : JpaRepository<Receita, UUID>