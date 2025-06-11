package ar.com.gabriel.cart.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import ar.com.gabriel.cart.domain.model.emun.CartStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gabriel Gonzalez
 */
@Entity
@Data
@NoArgsConstructor
@Table(
    name = "cart",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "status"})
)
public class Cart {
    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    private CartStatus status;

    private Double subtotal;
    private Double discount;
    private Double total;

    // La idea de usar el version es para manejar la concurrencia, si Cart se lee mas de una vez
    // Quien intente actualizarlo debe tener la version correcta, de lo contrario sale por una excepción
    // que se debe manejar en el servicio
    @Version
    private long version;

    public Cart(AppUser user) {
        this.user = user;
    }

    public void addItem(CartItem item) {
        this.items.add(item);
        item.setCart(this);
    }

    public void removeItem(CartItem item) {
        this.items.remove(item);
        item.setCart(null);
    }

     public void addOrIncrementItem(Product product, int qtyToAdd) {

        int qty = qtyToAdd <= 0 ? 1 : qtyToAdd;

        Optional<CartItem> itemOpt = items.stream()
                .filter(ci -> ci.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (itemOpt.isPresent()) {
            CartItem item = itemOpt.get();
            item.setQty(item.getQty() + qty);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQty(qty);
            newItem.setCart(this);
            items.add(newItem);
        }
    }

    public void decrementOrRemoveItem(UUID productId) {

        CartItem item = items.stream()
                .filter(ci -> ci.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item con producto " + productId + " no encontrado"));

        if (item.getQty() <= 1) {
            items.remove(item);   
        } else {
            item.setQty(item.getQty() - 1);
        }
    }
}