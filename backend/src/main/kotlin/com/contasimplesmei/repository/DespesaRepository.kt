package com.contasimplesmei.repository

import com.contasimplesmei.model.Despesa
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface DespesaRepository : JpaRepository<Despesa, UUID>