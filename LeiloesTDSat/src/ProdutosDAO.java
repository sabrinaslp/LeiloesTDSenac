/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {

        conn = new conectaDAO().connectDB();

        try {
            String query = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(query);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "O produto foi cadastrado com sucesso!");

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + e.getMessage());

        }

    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        conn = new conectaDAO().connectDB();

        try {
            String query = "SELECT * FROM produtos";
            prep = conn.prepareStatement(query);
            resultset = prep.executeQuery();

            listagem.clear();

            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);

            }

        } catch (SQLException erro) {

            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + erro.getMessage());

        } finally {

            try {
                if (resultset != null) {
                    resultset.close();
                }
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {

            }
        }

        return listagem;
    }
    
     public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();

        try {
            String query = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
            prep = conn.prepareStatement(query);
            prep.setInt(1, id);

            prep.executeUpdate();

            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso.");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao tentar vender o produto: " + erro.getMessage());
            
        } finally {

            try {
                if (prep != null) {
                    prep.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
              
            }
        }
    }
    
}
