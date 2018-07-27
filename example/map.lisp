(define map (lambda (f l) (cond ((eq l ()) ())
                                (t (cons (f (car l)) (map f (cdr l)))))))


(map (lambda (e) (atom e)) (quote (1 2 3)))

(map (lambda (e) (atom e)) (quote (1 (2) 3)))

(map (lambda (e) (car e)) (quote ((a . 1) (b . 2) (c . 3))))


(define wrap (lambda (e) (cons e ())))

(map wrap (quote (a b c d e)))

(map wrap (quote ((a . b) c (d . e) f)))
