# SrEmpregos

[![GitHub stars](https://img.shields.io/github/stars/SrBlecaute01/SrEmpregos.svg)](https://github.com/SrBlecaute/SrEmpregos/stargazers)
[![GitHub All Releases](https://img.shields.io/github/downloads/SrBlecaute01/SrEmpregos/total.svg?logoColor=fff)](https://github.com/SrBlecaute01/SrEmpregos/releases/latest)
[![GitHub issues](https://img.shields.io/github/issues-raw/heroslender/HeroMagnata.svg?label=issues)](https://github.com/heroslender/HeroMagnata/issues)
[![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/48103a1b283946fa8863d2c6d964375e)](https://www.codacy.com/manual/SrBlecaute01/SrEmpregos?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SrBlecaute01/SrEmpregos&amp;utm_campaign=Badge_Grade)

  Plugin de empregos com suporte a Legendchat, PlaceholderAPI e MVdWPlaceholderAPI. Atualmente existem 7 tipos de empregos que tem suas funções configuráveis: Minerador, Escavador, Lenhador, Caçador, Assassino, Pescador e Fazendeiro. 
  O sistema conta com um sistema de salário toda vez que o jogador conseguir bater a meta estipulada. Além disso, existe um sistema de quests que pode aumentar o salário do jogador.

![Preview](https://github.com/SrBlecaute01/SrEmpregos/raw/master/assets/Screenshot_2.png)

## Dependências
  
  O plugin necessita que tenha o Vault instalado em seu servidor e algum plugin de economia. 

### Comandos

- `/empregos` - Visualiza o menu de empregos

#### Placeholders

-   `{empregos_player_emprego}`      : nome do emprego do jogador
-   `{empregos_player_meta_total}`   : total de ações efetuadas (quebrar, cortar, pescar etc)
-   `{empregos_player_current}`      : total de ações efetuadas para receber o salário
-   `{empregos_player_quests}`       : quantidade de quests concluidas
-   `{empregos_player_salary}`       : salário atual do jogador
-   `{empregos_player_progress}`     : progresso numérico para receber osalário
-   `{empregos_player_progress_bar}` : barra de progresso para receber o salário
 
### Config

```yaml
#   _________  ____  _       ___    __  ____ __ __ ______   ___
#  / ___/    \|    \| |     /  _]  /  ]/    |  |  |      | /  _]
# (   \_|  D  )  o  ) |    /  [_  /  /|  o  |  |  |      |/  [_
#  \__  |    /|     | |___|    _]/  / |     |  |  |_|  |_|    _]
#  /  \ |    \|  O  |     |   [_/   \_|  _  |  :  | |  | |   [_
#  \    |  .  \     |     |     \     |  |  |     | |  | |     |
#   \___|__|\_|_____|_____|_____|\____|__|__|\__,_| |__| |_____|
#       plugin de empregos    Versão: 1.0-SNAPSHOT

# =========================
#                         MySQL
# caso desativado irá usar sqlite como forma de armazenamento.
MySQL:
  ativado: false
  host: 'localhost'
  porta: '3306'
  database: ''
  usuario: ''
  senha: ''

# Sons do sistema, mais sons em -> https://github.com/Attano/Spigot-1.8/blob/master/org/bukkit/Sound.java
Sons:
  Erro: 'NOTE_BASS'
  Geral: 'ITEM_PICKUP'
  Sucesso: 'NOTE_PLING'

# mensagens do plugin
Mensagens:
  Sem-Emprego: '&cVocê não tem nenhum emprego para poder fazer isso!'
  Com-Emprego: '&cVocê já tem um emprego! peça demissão para exercer este cargo'
  Foi-Contratado: '&aVocê foi contratado para o emprego de @emprego'
  Pediu-Demissao: '&cVocê pediu demissão do seu emprego ;('

  Recebeu-Pagamento:
  - ''
  - ' &eEmprego: &7você recebeu seu salário de &2R$ &f@salario'
  - ''

Preferencias:
  # tag do emprego no legendchat {empregos}
  Chat-Tag: true

  # nome do emprego da placeholder caso o jogador não tenha nenhum
  Placeholder:
    Desempregado: '&cSem emprego'

  # barra de progresso 
  Barra-De-Progress:
    # símbolo da barra
    Barra: '|'
    # quatidade de barras
    Quantidade: 10
    # cor da barra completa
    Cor-Completa: '&a'
    # cor da barra imcompleta
    Cor-Imcompleta: '&a'
    # primeiro simbolo da barra
    Primeiro: '&8['
    # último símbolo da barra
    Ultimo: '&8]'

Empregos-Gui:
  # nome da gui de empregos
  Nome: '&8Empregos'
  # ficará na lore do item
  Sem-Emprego: '&cDesempregado'
  Seleionar-Emprego: '&aClique para selecionar esse emprego'
  Emprego-Selecionado: '&cVocê já está nesse emprego'

    #   @emprego : nome do emprego
    #   @tag     : tag do emprego
    #   @meta    : meta para receer o salário
    #   @salario : salário do emprego
    #   @quests   : número de quests

  Minerador:
    # se ativado ele irá ignorar o item
    Skull:
      Ativado: false
      Url: ''
    # id do item no formato id:data
    ID-Data: '278:0'
    # nome do item
    Nome: '&eMinerador'
    # lore do item
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &cminerar @meta blocos'
      - '&7Quests: &e@quests'
      - ''

  Escavador:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '277:0'
    Nome: '&eEscavador'
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &cescavar @meta blocos'
      - '&7Quests: &e@quests'
      - ''

  Lenhador:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '279:0'
    Nome: '&eLenhador'
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &ccortar @meta madeiras'
      - '&7Quests: &e@quests'
      - ''

  Fazendeiro:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '293:0'
    Nome: '&eFazendeiro'
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &ccolher @meta plantas'
      - '&7Quests: &e@quests'
      - ''

  Pescador:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '346:0'
    Nome: '&ePescador'
    Lore:
    - '&7Salário Inicial: &2R$ &f@salario'
    - '&7Meta: &cpescar @meta peixes'
    - '&7Quests: &e@quests'
    - ''

  Cacador:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '261:0'
    Nome: '&eCaçador'
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &ccaçar @meta mobs'
      - '&7Quests: &e@quests'
      - ''

  Assassino:
    Skull:
      Ativado: false
      Url: ''
    ID-Data: '276:0'
    Nome: '&eAssassino'
    Lore:
      - '&7Salário Inicial: &2R$ &f@salario'
      - '&7Meta: &cmatar @meta jogadores'
      - '&7Quests: &e@quests'
      - ''

Item-Confirmar:
  Skull:
    Ativado: true
    Url: 'http://textures.minecraft.net/texture/8d82fcfa5578715c0d248e0aac42ab572e9a826ed3dad9dc66c9926e8473ed'
  ID-Data: '35:5'
  Nome: '&aConfirmar'
  Lore:
    - '&7Clique para confirmar'

Item-Cancelar:
  Skull:
    Ativado: true
    Url: 'http://textures.minecraft.net/texture/bc2e972afa9115b6d32075b1f1b7fed7aa29a5341c1024288361abe8e69b46'
  ID-Data: '35:14'
  Nome: '&cCancelar'
  Lore:
    - '&7Clique para cancelar'

Item-Pedir-As-Contas:
  Skull:
    Ativado: false
    Url: ''
  ID-Data: '395:0'
  Nome: '&cPedir Demissão'
  Lore:
    - '&7Se sair do seu emprego todo o seu'
    - '&7progresso conquistado será perdido!'

Item-Conquistas:
  Skull:
    Ativado: false
    Url: ''
  ID-Data: '399:0'
  Nome: '&aSuas conquistas'
  Lore:
    - '&7Veja suas conquistas'

#
#   @player   : nome do jogador
#   @emprego  : nome do emprego do jogador
#   @tag      : tag do emprego do jogador
#   @current  : meta para receber o salário
#   @meta     : meta para receber o salário
#   @total    : total de blocos, peixes ou entidades quebradas, pescadas ou mortas.
#   @salario  : salário do jogador
#   @quests   : número de quests concluidas.
#
Item-Info:
  Nome: '&e@player'
  Lore:
    - '&7Emprego Atual: &f@emprego'
    - '&7Salário Atual: &2R$ &f@salario'
    - ''
    - '&7Meta: &a@current&7/&c@meta'
    - '&7Total: @total'
    - '&7Quests concluidas: &e@quests'

# @emprego    : nome do emprego da quest
# @tipo1      : ação efetuada
# @tipo2      : tipo de bloco/entidade válida
Item-Quest:
  Tipos:
    Minerador: 'minerar-minérios'
    Lenhador: 'cortar-madeiras'
    Fazendeiro: 'plantar-plantas'
    Cacador: 'caçar-mobs'
    Pescador: 'pescar-peixes'
    Assassino: 'assassinar-jogadores'
    Escavador: 'escavar-blocos'

  Nome: '&aDesafio do emprego @emprego'
  Lore:
    - '&7O objetivo deste emprego é @tipo1 &e@quantia &7@tipo2'
    - ''
```

##### API

- [EmployeeAPI](https://github.com/SrBlecaute01/SrEmpregos/blob/master/src/main/java/br/com/blecaute/empregos/apis/EmployeeAPI.java) - classe para manusear os trabalhadores.
- [JobAPI](https://github.com/SrBlecaute01/SrEmpregos/blob/master/src/main/java/br/com/blecaute/empregos/apis/JobAPI.java) - classe para manusear os empregos.

###### Eventos

- `JobContractEvent` - Chamado quando um jogador é contratado para um emprego.
- `JobDismissEvent` - Chamado quando um jogador pede demissão do emprego.
- `JobGetPaymentEvent` - Chamado quando o jogador recebe seu pagamento.
- `JobQuestCompleteEvent` - Chamado quando o jogador completa uma quest.
